package com.myhandjava.momentours.randomquestion.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "tb_randomreply")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RandomReply {

    @Id
    @Column(name = "random_reply_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int randomreplyno;

    @Column(name = "random_reply_content")
    private String randomreplycontent;

    @Column(name = "random_reply_user_no")
    private int randomreplyuserno;

    @Column(name = "random_reply_question_no")
    private int randomreplyquestionno;

    @Column(name = "random_reply_is_deleted")
    private int randomreplyisdeleted;

    @Column(name = "random_couple_no")
    private int randomcoupleno;

}
