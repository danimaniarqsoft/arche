<template>
  <div>
    <b-sidebar
      id="sidebar-right"
      :visible="true"
      title="Sidebar"
      shadow
      no-close-on-backdrop
      no-close-on-esc
      no-close-on-route-change
      no-enforce-focus
      no-header
      no-header-close
    >
      <template #default="">
        <div class="p-3">
          <h4 id="sidebar-no-header-title">Secciones</h4>
          <b-nav vertical class="w-25">
            <b-nav-item to="/soluciones" exact exact-active-class="active" class="d-inline-block">
              <span v-text="$t('global.menu.entities.soluciones')">Soluciones</span>
            </b-nav-item>
            <b-nav-item to="/solicitud" exact exact-active-class="active">
              <span v-text="$t('global.menu.entities.solicitud')">Solicitud</span></b-nav-item
            >
          </b-nav>
        </div>
      </template>
    </b-sidebar>
  </div>
</template>

<script lang="ts" src="./side-navbar.component.ts"></script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped></style>
