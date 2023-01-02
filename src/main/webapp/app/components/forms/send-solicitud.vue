<template>
  <div>
    <header class="mt-5 bx-header-title">
      <b-card border-variant="primary">
        <div v-html="terminos"></div>
        <hr />
        <b-form-checkbox id="terminos-condiciones-id" v-model="accepted" name="terminos-condiciones" size="lg">
          Aceptar términos y condiciones
        </b-form-checkbox>
      </b-card>

      <div class="mt-4" v-if="accepted">
        <small id="btnProcesoFirmaHelp" class="mb-2 form-text text-muted">Por favor oprima el botón para enviar su solicitud</small>
        <b-button block variant="primary" v-b-modal.send-confirmation-modal>
          <span>Enviar solicitud</span>
          <span class="icon-mas"></span>
        </b-button>

        <confirmation id="send-confirmation-modal" @confirmed="handleConfirmation"></confirmation>
      </div>
    </header>
  </div>
</template>

<script lang="ts" src="./send-solicitud.component.ts"></script>

<style scoped></style>
