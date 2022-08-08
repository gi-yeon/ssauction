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
// npm install universal-cookie
import { useCookies } from '@vueuse/integrations/useCookies';

export default {
    name: "BoardRegister",
    setup() {
        const router = useRouter();
        const cookies = useCookies(['login.userNo', 'login.userNickname']);

        const article = reactive({
            title: "",
            content: "",
        });

        const registerArticle = () => {
            let params = {
                boardTitle: article.title,
                boardContent: article.content,
                boardType: 0,
                userNo: cookies.get('login.userNo'),
                userNickname: cookies.get('login.userNickname')
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