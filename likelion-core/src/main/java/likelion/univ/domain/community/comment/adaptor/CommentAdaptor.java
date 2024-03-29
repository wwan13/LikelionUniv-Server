package likelion.univ.domain.community.comment.adaptor;

import likelion.univ.annotation.Adaptor;
import likelion.univ.domain.community.comment.entity.Comment;
import likelion.univ.domain.community.comment.exception.CommentNotFoundException;
import likelion.univ.domain.community.comment.repository.CommentRepository;
import likelion.univ.domain.community.post.entity.Post;
import lombok.RequiredArgsConstructor;

import java.util.List;


@Adaptor
@RequiredArgsConstructor
public class CommentAdaptor {
    private final CommentRepository commentRepository;

    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    public Comment findById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(CommentNotFoundException::new);
    }
    public void delete(Comment comment) {
        commentRepository.delete(comment);
    }

    public List<Comment> findAllByPost(Post post) {
        return commentRepository.findByPost(post);
    }

}
