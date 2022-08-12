<template>
    <div>
        <input type="text" name="title" id="title" v-model="state.boardTitle">
        <div></div>
        <textarea name="content" id="content" cols="30" rows="10" v-model="state.boardContent">

        </textarea>

        <div>
            <button @click="ModifyArticle">글수정하기</button>
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
    name: "BoardModify",
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
            let params = {
                boardNo : state.boardNo,
                boardTitle : state.boardTitle,
                boardContent : state.boardContent
            }

            axios.put("board", JSON.stringify(params))
            .then(() => {
                alert("수정이 완료되었습니다.");
            })
            router.push({name: "boardDetail", params: {id: state.boardNo}});
        }

        axios.get("board/detail", {
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
            ModifyArticle
        }
    }

}
</script>