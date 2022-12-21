<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <div>
          <formio ref="formioForm" :form="form" :options="options" v-on:submit="handleSubmit" v-on:formLoad="handleFormLoad"></formio>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./solicitud-update.component.ts"></script>
