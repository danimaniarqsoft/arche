<template>
  <b-modal :ref="id" header-bg-variant="primary" :id="id">
    <span slot="modal-title"
      ><span>{{ title }}</span></span
    >
    <div class="modal-body">
      <p>{{ question }}</p>
    </div>
    <div slot="modal-footer">
      <b-button variant="outline-danger" @click="handleCancel()">{{ $t('entity.action.cancel') }}</b-button>
      <b-button variant="primary" @click="handleConfirmation()">{{ $t('entity.action.confirm') }}</b-button>
    </div>
  </b-modal>
</template>

<script lang="ts" src="./confirmation.component.ts"></script>
