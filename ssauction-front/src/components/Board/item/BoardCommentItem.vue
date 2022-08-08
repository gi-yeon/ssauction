<template>
    <div>
        <div>{{commentContent}}</div>
        <div>{{userNickname}}</div>
        <div>{{commentRegDate}}</div>
        <button v-show="state.isWriter" @click="deleteComment()">삭제</button>
    </div>
</template>

<script>
import axios from '@/utils/axios';
import {reactive} from "vue";
import { useRouter } from 'vue-router';
// npm install universal-cookie
import { useCookies } from '@vueuse/integrations/useCookies';

export default {
    name: "BoardCommentItem",
    props: {
        commentNo: Number,
        commentContent: String,
        commentRegDate: String,
        boardNo: Number,
        userNo: Number,
        userNickname: String,
    },
    setup(props) {
        const router = useRouter();
        const cookies = useCookies(['login.userNo']);

        const state = reactive({
            isWriter: Boolean
        });
        if(cookies.get('login.userNo') != null && props.userNo == cookies.get('login.userNo') )
            state.isWriter = true;
        else
            state.isWriter = false;

        const deleteComment = () => {
            axios.delete("http://localhost:8080/comment", {
                params: {
                    commentsNo: props.commentNo,
                    userNo: cookies.get('login.userNo')
                },
            })
            .then(() => {
                alert("댓글을 삭제하였습니다.");
                router.go();
            })
        }

        return {
            state,
            deleteComment
        }
    }
}
</script>
