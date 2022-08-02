<template>
    <div class="table">
        <h1>BoardList</h1>
        <table class="boardtable">
            <colgroup>
                <col style="width: 10%" />
                <col style="width: 50%" />
                <col style="width: 20%" />
                <col style="width: 20%" />
            </colgroup>
            <thead>
                <tr>
                    <th>글번호</th>
                    <th>제목</th>
                    <th>작성자</th>
                    <th>작성일</th>
                </tr>
            </thead>
            <tbody>
                <board-list-item
                    v-for="(article, index) in state.articles"
                    :key="index"
                    v-bind="article"
                ></board-list-item>
            </tbody>
        </table>

    </div>

</template>

<script>
import BoardListItem from "@/components/Board/item/BoardListItem";
import axios from '@/utils/axios';
import {reactive} from "vue";

export default {
    name: "BoardList",
    components: {
        BoardListItem,
    },
    setup() {
        const state = reactive({
            articles: [],
        });
        axios.get("http://localhost:8080/board", {
            params: {
                page: 0,
                size: 10,
            },
        })
        .then(({data}) => {
            console.log(data.list.content);
            state.articles = data.list.content;
        })

        return {
            state
        }
    }
}
</script>