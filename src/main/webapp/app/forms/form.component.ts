import Component from 'vue-class-component';
import { Vue, Inject } from 'vue-property-decorator';
import LoginService from '@/account/login.service';
import '@jsonforms/vue2-vanilla/vanilla.css';
// mergeStyles combines all classes from both styles definitions

@Component({
  components: {},
})
export default class Form extends Vue {
  @Inject('loginService')
  private loginService: () => LoginService;
  success = false;
  error = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.query.key) {
        vm.init(to.query.key);
      }
    });
  }

  public mounted() {}
  public formUrl = 'http://localhost:3001/form/639ce61bc69806cc78b482bc';
  public options = { readOnly: false, languaje: 'en', viewAsHtml: false };
  public init(key: string): void {}

  public openLogin(): void {
    this.loginService().openLogin((<any>this).$root);
  }
  public enviar() {
    //console.log(<any>this.$refs.formioForm);
    console.log('Hola mundo como están todos');
  }
}
