<template>
  <div>
    <div><img src="" /></div>
    <div>
      <div>{{ houseTitle }}</div>
      <div>{{ userName }}</div>
      <div v-if="isSelling">{{ participantNum }}</div>
      <div v-if="!isSelling">{{ houseAuctionDate }}</div>
      <div v-if="isSelling">
        <div>{{ houseAuctionCurrentPrice }}</div>
        <div>{{ houseAuctionRemainingTime }}</div>
      </div>
      <div v-if="!isSelling">
        <div>{{ houseDesc }}</div>
      </div>
    </div>
  </div>
</template>

<script>
export default {};
</script>

<style></style>
