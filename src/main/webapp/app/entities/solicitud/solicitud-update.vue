<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <div>
          <div v-show="showWelcomeMessage" v-html="solucion.mensaje.bienvenida"></div>
          <formio
            v-if="isFormioVisible"
            :submission="formContext.submission"
            ref="formio"
            :form="form"
            :options="options"
            v-on:submit="handleSubmit"
            v-on:formLoad="handleFormLoad"
          ></formio>
          <send-solicitud
            v-if="isSendVisible"
            :isSent="isSolicitudSent"
            @send="handleSend"
            :terminos="solucion.mensaje.terminos"
          ></send-solicitud>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./solicitud-update.component.ts"></script>
