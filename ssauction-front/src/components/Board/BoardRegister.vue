<template>
    <div>
        <input type="text" name="title" id="title" v-model="article.title">
        <div></div>
        <textarea name="content" id="content" cols="30" rows="10" v-model="article.content">

        </textarea>

        <div>
            <button @click="registerArticle">글작성</button>
            <router-link to="/board">
                <button>목록</button>
            </router-link>
        </div>
    </div>
</template>

<script>
import {reactive} from "vue";
import axios from '@/utils/axios';
import {useRouter } from 'vue-router';
// import { state } from "store/user.vue";

export default {
    name: "BoardRegister",
    setup() {
        const router = useRouter();

        const article = reactive({
            title: "",
            content: "",
        });

        const registerArticle = () => {
            let params = {
                boardTitle: article.title,
                boardContent: article.content,
                boardType: 0,
                userNo: 1,
                userNickname: "ssafy"
            };
            axios.post("http://localhost:8080/board", JSON.stringify(params))
            .then(() => {})
            .catch(() => {
                alert("글 등록에 실패하였습니다.");
            })
            
            router.push({path: "/board"});
        }

        return {
            article,
            registerArticle
        }
    }
}
</script>