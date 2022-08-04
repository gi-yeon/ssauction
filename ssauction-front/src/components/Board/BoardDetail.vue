<template>
    <div>
        <input type="text" name="title" id="title" v-model="state.boardTitle" readonly>
        <div></div>
        <textarea name="content" id="content" cols="30" rows="10" v-model="state.boardContent" readonly>

        </textarea>

        <div>
            <button @click="ModifyArticle">글수정</button>
            <button @click="DeleteArticle">글삭제</button>
            <router-link to="/board">
                <button>목록</button>
            </router-link>
        </div>
    </div>
</template>

<script>
import axios from '@/utils/axios';
import {reactive} from "vue";
import { useRouter, useRoute } from 'vue-router';

export default {
    name: "BoardDetail",
    setup() {
        const router = useRouter();
        const route = useRoute();

        const state = reactive({
            boardNo: Number,
            boardTitle: String,
            boardContent: String,
            userNo: Number,
            userNickname: String,
        });

        const ModifyArticle = () => {
            router.push({name: "boardModify", params: {id: state.boardNo}});
        }

        const DeleteArticle = () => {
            axios.delete("http://localhost:8080/board", {
                params: {
                    boardNo: route.params.id
                },
            })
            .then(() => {
                alert("게시글을 삭제하였습니다.");
            })

            router.push({path: "/board"});
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
        })

        return {
            state,
            ModifyArticle,
            DeleteArticle
        }
    }

}
</script>