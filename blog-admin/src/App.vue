<template>
  <div id="app">
    <router-view/>
  </div>
</template>

<style>
::-webkit-scrollbar {
  width: 6px;
  height: 8px;
  background-color: #ebeef5;
}
::-webkit-scrollbar-thumb {
  box-shadow: inset 0 0 6px rgba(236, 10, 10, 0.3);
  -webkit-box-shadow: inset 0 0 6px rgba(211, 14, 14, 0.3);
  background-color: #97dffd;
}
::-webkit-scrollbar-track{
  box-shadow: inset 0 0 5px rgb(51, 231, 10);
  border-radius: 3px;
  background: rgba(255, 255, 255, 1);
}
</style>
