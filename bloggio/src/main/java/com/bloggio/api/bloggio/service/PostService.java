package com.bloggio.api.bloggio.service;

import com.bloggio.api.bloggio.dto.PostListDTO;
import com.bloggio.api.bloggio.dto.PostSaveDTO;
import com.bloggio.api.bloggio.exception.Exception;
import com.bloggio.api.bloggio.mapper.PostMapperImpl;
import com.bloggio.api.bloggio.payload.post.request.PostLikeUpdateRequest;
import com.bloggio.api.bloggio.persistence.entity.Post;
import com.bloggio.api.bloggio.persistence.projection.PostByFilters;
import com.bloggio.api.bloggio.persistence.repository.CommentRepository;
import com.bloggio.api.bloggio.persistence.repository.PostRepository;
import com.bloggio.api.bloggio.persistence.repository.UsersRepository;
import com.cloudinary.Cloudinary;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;


@Service
@Log4j2
@AllArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    private final CommentRepository commentRepository;

    private final UsersRepository usersRepository;

    private final PostMapperImpl postMapper;

    @Resource
    private Cloudinary cloudinary;


    public String create(PostSaveDTO postSaveDTO, MultipartFile file) {
        Post postSave;
        log.info("try save");
        try {
            String url = uploadFile(file, "bloggio");
            Post post = postMapper.postDtoToPost(postSaveDTO);
            post.setPostImage(url);
            postSave = postRepository.save(post);
            log.info("save post into db");
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }

        return postSave.getPostId().toString();
    }

    public String createV2(PostSaveDTO postSaveDTO, MultipartFile file) {
        Post postSave;
        log.info("try save");
        try {
            String url = uploadFile(file, "bloggio");
            Post post = postMapper.postDtoToPost(postSaveDTO);
            post.setPostImage(url);
            postSave = postRepository.save(post);
            log.info("save post into db");
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }

        return postSave.getPostId().toString();
    }

    public List<PostListDTO> findAll() {
        return postMapper.postsToPostListDTO(postRepository.findAll().stream().filter(post -> post.getPostState() == 1)
                .collect(Collectors.toList()));
    }

    /*public List<PostListDTO> getTop4Post(){
        return postMapper.postsToPostListDTO(postRepository.findTop4ByOrderByPostLikesDesc());
    }*/

    public List<PostByFilters> getTop4Post() {
        return postRepository.getTop4PostByLikes();
    }

    public PostListDTO findById(String postId) {
        UUID uuid = UUID.fromString(postId);
        Optional<Post> post = postRepository.findById(uuid);
        return post.map(postMapper::postToPostWithUserDTO).orElse(null);
    }

    @Transactional
    public void update(PostSaveDTO postSaveDTO, MultipartFile file) {
        Post postUpdate;
        log.info("try update");
        try {
            UUID uuid = postSaveDTO.getPostId();
            Optional<Post> post = postRepository.findById(uuid);
            if (post.isEmpty()) {
                log.error("Post With Id " + uuid + " Not Found");
                throw new Exception("Post Not Found", HttpStatus.NOT_FOUND);
            }
            String url = post.get().getPostImage();
            if (postSaveDTO.isHasImageForUpload()){
                url = uploadFile(file, "bloggio");
            }
            postUpdate = postMapper.postDtoToPost(postSaveDTO);
            postUpdate.setPostImage(url);
        } catch (java.lang.Exception e) {
            log.error(e.getMessage());
            throw e;
        }

        postRepository.updatePost(postUpdate.getPostId(), postUpdate.getPostContent(), postUpdate.getPostDescription(), postUpdate.getPostImage(),
                postUpdate.getPostLikes(), postUpdate.getPostPriority(), postUpdate.getPostState(), new Date(),
                postUpdate.getPostTitle(), postUpdate.getPublished());
    }

    public void delete(String postId) {
        UUID uuid = UUID.fromString(postId);
        Optional<Post> post = postRepository.findById(uuid);
        if (post.isEmpty()) {
            log.error("Post With Id " + postId + " Not Found");
            throw new Exception("Post Not Found", HttpStatus.NOT_FOUND);
        }
        commentRepository.deleteAllCommentsByPostId(post.get().getPostId());
        postRepository.deleteById(post.get().getPostId());
    }

    public String uploadFile(MultipartFile file, String folderName) {
        try {
            HashMap<Object, Object> options = new HashMap<>();
            options.put("folder", folderName);
            Map uploadedFile = cloudinary.uploader().upload(file.getBytes(), options);
            String publicId = (String) uploadedFile.get("public_id");
            return cloudinary.url().secure(true).generate(publicId);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<PostByFilters> getAllPostByDateAndPage(int offset, int limit) {
        return postRepository.getAllPostByDateAndPage(offset, limit);
    }

    public List<PostByFilters> getAllPostByFilters(int offset, int limit, String category_name, String post_title,
                                                   String post_creation_start, String post_creation_end) {
        String categoryFormat = String.format("%%%s%%", category_name);
        String postFormat = String.format("%%%s%%", post_title);
        return postRepository.getAllPostByFilter(offset, limit, categoryFormat, postFormat, convertToLocalDate(post_creation_start), convertToLocalDate(post_creation_end));
    }

    private LocalDate convertToLocalDate(String date) {
        final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        if (Objects.nonNull(date) && !date.isEmpty()) {
            return LocalDate.parse(date, dtf);
        }

        return null;
    }

    public List<PostByFilters> getPostByUser(int offset, int limit, String userId) {
        UUID uuid = UUID.fromString(userId);
        return postRepository.getPostsByUserId(offset, limit, userId);
    }

    public void updateLike(String postId, PostLikeUpdateRequest postLikeUpdateRequest, String type) {
        UUID uuid = UUID.fromString(postId);
        Optional<Post> post = postRepository.findById(uuid);
        if (post.isEmpty()) {
            log.error("Post With Id " + postId + " Not Found");
            throw new Exception("Post Not Found", HttpStatus.NOT_FOUND);
        }
        int likesdb = post.get().getPostLikes() == null ? 0 : post.get().getPostLikes();
        if (type.equals("add")) {
            post.get().setPostLikes(likesdb + 1);
        } else {
            post.get().setPostLikes(likesdb - 1);
        }
        PostSaveDTO postUpdate = postMapper.postToPostDTO(post.get());

        PostSaveDTO result = postMapper.postToPostDTO(postRepository.save(postMapper.postDtoToPost(postUpdate)));
        if (Objects.nonNull(result)) {
            log.debug("se actualizo correctamente!!");
        }
    }

    public List<PostByFilters> getRecommendedPost(String user, String categoryName) {
        List<PostByFilters> postsByUserList = postRepository.getPostsByUserId(1, 4, user);
        if (postsByUserList.size() == BigDecimal.ONE.intValue()) {
            return postRepository.getAllPostByFilter(1, 4, categoryName, null, null, null);
        }
        return postsByUserList;
    }

}
