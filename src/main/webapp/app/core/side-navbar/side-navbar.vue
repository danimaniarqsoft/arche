<template>
  <div>
    <b-sidebar
      id="sidebar-right"
      :visible="isSideNavbarActivated"
      title="Secciones"
      shadow
      no-close-on-backdrop
      no-close-on-esc
      no-close-on-route-change
      no-enforce-focus
      no-header-close
    >
      <template #default="">
        <div class="p-3">
          <div class="mb-2 text-center">
            <b-avatar size="6rem" variant="success" rounded></b-avatar>
          </div>
          <hr />
          <message v-if="!menu.componentes || menu.componentes <= 0" description="No hay elementos registrados"></message>
          <b-nav vertical class="w-100">
            <b-nav-item
              v-for="item in menu.componentes"
              v-bind:key="item._id"
              @click="loadForm(item)"
              exact
              exact-active-class="active"
              class="d-inline-block"
            >
              <b-icon :icon="item.icon" variant="success"></b-icon>
              {{ item.titulo }}
            </b-nav-item>
          </b-nav>
          <hr />
          <b-nav vertical class="w-100">
            <b-nav-item v-if="menu.isSendActivated" @click="loadSendForm" exact exact-active-class="active" class="d-inline-block">
              <b-icon icon="box-arrow-right" variant="success"></b-icon>
              Enviar solicitud
            </b-nav-item>
          </b-nav>
          <div v-if="menu.isPreviewActivated">
            <hr />
            <b-button @click="handleActivatePreview(true)" v-if="!isPreview" block variant="outline-success"
              ><b-icon icon="eye-slash"></b-icon> Activar vista previa</b-button
            >
            <div v-else>
              <b-button @click="handleActivatePreview(false)" block variant="danger"
                ><b-icon icon="eye" animation="fade"></b-icon> Desactivar vista previa</b-button
              >
              <message
                description="La vista previa se encuentra activada, puede navegar entre las diferentes opciones del menú para visualizar el resultado"
              ></message>
            </div>
          </div>
        </div>
      </template>
    </b-sidebar>
  </div>
</template>

<script lang="ts" src="./side-navbar.component.ts"></script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.badge-success {
  color: #ffff;
}
</style>
