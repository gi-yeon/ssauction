<template>


    <div>
        <input type="text" name="title" id="title" v-model="state.boardTitle" readonly>
        <div></div>
        <textarea name="boardContent" id="boardContent" cols="30" rows="10" v-model="state.boardContent" readonly>

        </textarea>

        <div>
            <button v-show="state.isWriter" @click="ModifyArticle">글수정</button>
            <button v-show="state.isWriter" @click="DeleteArticle">글삭제</button>
            <router-link to="/board">
                <button>목록</button>
            </router-link>
        </div>
            <board-comment-item v-for="(comment, index) in state.comments"
            :key="index"
            v-bind="comment"
            ></board-comment-item>
            <div>
                <textarea name="commentRegister" id="commentRegister" cols="30" rows="2" v-model="state.commentContent"></textarea>
                <button @click="registerComment">댓글 달기</button>
            </div>
        <div>
            
        </div>
    </div>
</template>

<script>
import axios from '@/utils/axios';
import {reactive} from "vue";
import { useRouter, useRoute } from 'vue-router';
import BoardCommentItem from "@/components/Board/item/BoardCommentItem";
// npm install universal-cookie
import { useCookies } from '@vueuse/integrations/useCookies';


export default {
    name: "BoardDetail",
    components: {
        BoardCommentItem,
    },
    setup() {
        const router = useRouter();
        const route = useRoute();
        const cookies = useCookies(['login.userNo']);

        const state = reactive({
            boardNo: Number,
            boardTitle: String,
            boardContent: String,
            userNo: Number,
            userNickname: String,
            comments: [],
            commentContent: "",
            isWriter: Boolean
        });

        const ModifyArticle = () => {
            router.push({name: "boardModify", params: {id: state.boardNo}});
        }

        const DeleteArticle = () => {
            axios.delete("http://localhost:8080/board", {
                params: {
                    boardNo: route.params.id,
                    userNo: cookies.get('login.userNo'),
                },
            })
            .then(() => {
                alert("게시글을 삭제하였습니다.");
            })

            router.push({path: "/board"});
        }

        const registerComment = () => {
            let params = {
                boardNo: state.boardNo,
                userNo: cookies.get('login.userNo'),
                commentContent: state.commentContent
            }
            axios.post("http://localhost:8080/comment", JSON.stringify(params))
            .then(() => {
                alert("댓글을 등록하였습니다.");
                router.go();
            })
        }

        axios.get("http://localhost:8080/board/detail", {
            params: {
                boardNo: route.params.id
            },
        })
        .then(({data}) => {
            state.boardNo = data.board.boardNo;
            state.boardTitle = data.board.boardTitle;
            state.boardContent = data.board.boardContent;
            state.userNo = data.board.userNo;
            state.userNickname = data.board.userNickname;
            state.comments = data.board.comments;

            if(cookies.get('login.userNo') != null && data.board.userNo == cookies.get('login.userNo') )
                state.isWriter = true;
            else
                state.isWriter = false;
        })

        return {
            state,
            ModifyArticle,
            DeleteArticle,
            registerComment
        }
    }

}
</script>