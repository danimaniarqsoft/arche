<template>
  <div>
    <header class="mt-5 bx-header-title">
      <message variant="info" title="Aviso de privacidad" :description="disclaimer"></message>

      <div class="form-group">
        <b-card variant="success">
          <b-form-checkbox id="terminos-condiciones-id" v-model="accepted" name="terminos-condiciones">
            Aceptar terminos y condiciones
          </b-form-checkbox>
        </b-card>

        <div v-if="accepted" class="card-body">
          <small id="btnProcesoFirmaHelp" class="mb-2 form-text text-muted">Por favor oprima el botón para enviar su solicitud</small>
          <b-button block variant="primary" v-b-modal.send-confirmation-modal>
            <span>Enviar solicitud</span>
            <span class="icon-mas"></span>
          </b-button>

          <confirmation id="send-confirmation-modal" @confirmed="handleConfirmation"></confirmation>
        </div>
      </div>
    </header>
  </div>
</template>

<script lang="ts" src="./send-solicitud.component.ts"></script>

<style scoped></style>
