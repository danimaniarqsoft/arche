<template>
  <div class="row justify-content-center">
    <div class="col-md-8">
      <div class="myform">
        <formio ref="formioForm" :src="formUrl" url="" form="" submission="" :options="options" language="en" v-on:submit="enviar"></formio>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./form.component.ts"></script>
