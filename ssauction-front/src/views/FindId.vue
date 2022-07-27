<template>
    <div id="app">
        <h2>{{PhoneNo}}</h2>
        <p>{{userId}}</p>
        <input type="text" :value="phoneNo" @input="inputPhoneNo" placeholder="폰번호를 입력해 주세요."/>
    </div>
</template>

<script>
    import {computed} from 'vue';
    import {useStore} from 'vuex';

    function useUser() {
        const store = useStore();

        const phoneNo = computed(() => store.state.user.phoneNo);
        const userId = computed(() => store.getters['user/userId']);

        return {
            phoneNo,
            userId,
        }
    }

    export default {
        name: 'App',
        setup() {
            return {
                ...useUser()
            }
        },
    }
</script>