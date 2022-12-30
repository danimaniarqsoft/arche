<template>
  <b-alert show :variant="variant">
    <h4 v-if="title" class="alert-heading"><b-icon :icon="icon"></b-icon> {{ title }}</h4>
    <p v-if="description">
      {{ description }}
    </p>
    <div v-if="footer">
      <hr />
      <p class="mb-0">{{ footer }}</p>
    </div>
  </b-alert>
</template>

<script lang="ts" src="./message.component.ts"></script>
