<template>
  <div>
    <hr />
    <message v-for="(error, index) in errors" :key="index" variant="warning" :title="error.title" :description="error.description" />
  </div>
</template>

<script lang="ts" src="./error.component.ts"></script>
