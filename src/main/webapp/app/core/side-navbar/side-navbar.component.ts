import { Component, Inject, Vue } from 'vue-property-decorator';
import LoginService from '@/account/login.service';
import AccountService from '@/account/account.service';
import TranslationService from '@/locale/translation.service';
import { Menu } from '@/shared/model/menu.model';
import EntitiesMenu from '@/entities/entities-menu.vue';
import { Componente } from '@/shared/model/componente.model';

@Component({
  components: {
    'entities-menu': EntitiesMenu,
  },
})
export default class JhiNavbar extends Vue {
  @Inject('loginService')
  private loginService: () => LoginService;
  @Inject('translationService') private translationService: () => TranslationService;

  @Inject('accountService') private accountService: () => AccountService;
  public version = 'v' + VERSION;
  private currentLanguage = this.$store.getters.currentLanguage;
  private languages: any = this.$store.getters.languages;
  private hasAnyAuthorityValues = {};
  private menu = new Menu();
  public isSideNavbarActivated = false;
  public isPreview = false;

  mounted() {
    (this.$root as any).$on('update-menu', data => {
      this.menu = data;
    });
    (this.$root as any).$on('show-side-navbar', data => {
      this.isSideNavbarActivated = data;
    });
  }

  public loadSendForm() {
    const componente = new Componente();
    componente.formId = 'send-component';
    componente.titulo = 'Enviar solicitud';
    componente.descripcion = 'enviarSolicitud';
    componente.orden = 1000;
    componente.icon = 'no-icon';
    componente.path = 'send/compponent';
    componente.tipo = 'send';
    this.loadForm(componente);
  }

  public loadCuestionarioForm() {
    const componente = new Componente();
    componente.formId = 'cuestionario-component';
    componente.titulo = 'Cuestionario';
    componente.descripcion = 'cuestionarioSolicitud';
    componente.orden = 2000;
    componente.icon = 'no-icon';
    componente.path = 'cuestionario/compponent';
    componente.tipo = 'review';
    this.loadForm(componente);
  }

  public loadForm(component) {
    (this.$root as any).$emit('load-form', component);
  }

  created() {
    const currentLanguage = Object.keys(this.languages).includes(navigator.language) ? navigator.language : this.currentLanguage;
    this.translationService().refreshTranslation(currentLanguage);
  }

  public subIsActive(input) {
    const paths = Array.isArray(input) ? input : [input];
    return paths.some(path => {
      return this.$route.path.indexOf(path) === 0; // current path starts with this path string
    });
  }

  public changeLanguage(newLanguage: string): void {
    this.translationService().refreshTranslation(newLanguage);
  }

  public isActiveLanguage(key: string): boolean {
    return key === this.$store.getters.currentLanguage;
  }

  public logout(): Promise<any> {
    localStorage.removeItem('jhi-authenticationToken');
    sessionStorage.removeItem('jhi-authenticationToken');
    this.$store.commit('logout');
    if (this.$route.path !== '/') {
      return this.$router.push('/');
    }
    return Promise.resolve(this.$router.currentRoute);
  }

  public handleActivatePreview(activate: boolean) {
    this.isPreview = activate;
    (this.$root as any).$emit('is-preview-activated', activate);
  }

  public get authenticated(): boolean {
    return this.$store.getters.authenticated;
  }

  public hasAnyAuthority(authorities: any): boolean {
    this.accountService()
      .hasAnyAuthorityAndCheckAuth(authorities)
      .then(value => {
        if (this.hasAnyAuthorityValues[authorities] !== value) {
          this.hasAnyAuthorityValues = { ...this.hasAnyAuthorityValues, [authorities]: value };
        }
      });
    return this.hasAnyAuthorityValues[authorities] ?? false;
  }
}
