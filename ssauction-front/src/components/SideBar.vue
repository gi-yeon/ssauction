<template>
  <div id="App" class="side target">
    <div>
      <br /><br />
      <router-link to="/"
        ><img src="@\assets\imgs\home.png" alt="Home" class="icon"
      /></router-link>
      <br /><br /><br /><br />
      <router-link to="profile"
        ><img src="@\assets\imgs\profile.png" alt="Profile" class="icon"
      /></router-link>
      <br /><br /><br /><br />
      <router-link to="zzim"
        ><img src="@\assets\imgs\zzim.png" alt="Zzim" class="icon"
      /></router-link>
      <br /><br /><br /><br />
      <router-link to="house"
        ><img src="@\assets\imgs\auction.png" alt="MakeHouse" class="icon"
      /></router-link>
      <br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br />
      <router-link to="settings"
        ><img src="@\assets\imgs\settings.png" alt="Settings" class="icon"
      /></router-link>
    </div>
  </div>
</template>

<style>
.side {
  height: 100%;
  width: 80px;
  background-color: rgb(255, 211, 182);
  position: fixed;
  top: 50;
  left: 0;
  overflow-x: hidden;
  padding-top: 0;
}

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
