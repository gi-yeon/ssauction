<template>
    <div>
        <div>{{commentContent}}</div>
        <div>{{userNo}}</div>
        <div>{{commentRegDate}}</div>
        <button @click="deleteComment()">삭제</button>
    </div>
</template>

<script>
import axios from '@/utils/axios';
import { useRouter } from 'vue-router';

export default {
    name: "BoardCommentItem",
    props: {
        commentNo: Number,
        commentContent: String,
        commentRegDate: String,
        boardNo: Number,
        userNo: Number
    },
    setup(props) {
        const router = useRouter();

        const deleteComment = () => {
            axios.delete("http://localhost:8080/comment", {
                params: {
                    commentsNo: props.commentNo,
                    userNo: 1
                },
            })
            .then(() => {
                alert("댓글을 삭제하였습니다.");
                router.go();
            })
        }

        return {
            deleteComment
        }
    }
}
</script>
