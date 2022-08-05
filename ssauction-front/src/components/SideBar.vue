<template>
  <div id="App">
    <el-container>
      <el-aside width="200px">
        <!-- <el-menu>
            <router-link to="/">
            <img src="@\assets\imgs\home.png" alt="Home" class="icon"/>
            </router-link>
        </el-menu> -->

      </el-aside>
    </el-container>
      <router-link to="/"
        ><img src="@\assets\imgs\home.png" alt="Home" class="icon"
      /></router-link>

      <router-link to="profile"
        ><img src="@\assets\imgs\profile.png" alt="Profile" class="icon"
      /></router-link>
  
      <router-link to="zzim"
        ><img src="@\assets\imgs\zzim.png" alt="Zzim" class="icon"
      /></router-link>

      <router-link to="house"
        ><img src="@\assets\imgs\auction.png" alt="MakeHouse" class="icon"
      /></router-link>
      <router-link to="settings"
        ><img src="@\assets\imgs\settings.png" alt="Settings" class="icon" style="position:fixed; bottom:15px; padding-left:0px"
      /></router-link>

  </div>
</template>

<style>

.icon {
  height: 40px;
  width: 40px;
}
.target {
  -ms-overflow-style: none; /* IE and Edge */
  scrollbar-width: none; /* Firefox */
}
.target::-webkit-scrollbar {
  display: none; /* Chrome, Safari, Opera*/
}
</style>
