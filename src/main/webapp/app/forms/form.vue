<template>
  <div class="row justify-content-center">
    <div class="col-md-8">
      <b-button v-on:click="enviar">I am a Button</b-button>
      <formio
        ref="formioForm"
        :src="formUrl"
        url="http://localhost:9000/forms"
        form=""
        submission=""
        :options="options"
        language="en"
        v-on:change="onChange"
        v-on:submit="enviar"
      ></formio>
    </div>
  </div>
</template>

<script lang="ts" src="./form.component.ts"></script>
