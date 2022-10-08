<template>
  <div class="row justify-content-center">
    <div class="col-md-8">
      <h1>hola mundo</h1>
      <div class="alert alert-success">
        <span><strong>Esto es un test</strong> Please </span>
      </div>
      <div class="myform">
        <json-forms :data="data" :renderers="renderers" :schema="schema" :uischema="uischema" @change="onChange" />
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./form.component.ts"></script>
