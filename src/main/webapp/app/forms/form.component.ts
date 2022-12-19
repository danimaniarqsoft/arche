import Component from 'vue-class-component';
import { Vue, Inject } from 'vue-property-decorator';
import LoginService from '@/account/login.service';

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
  public enviar(forms) {
    console.log('estamos enviando algo!!!!!!!!!!!!!!!!!!!');
    console.log(forms);
    //    (<any>this.$refs.formioForm).formio.submit();
    //console.log(<any>this.$refs.formioForm);
  }

  public onChange(schema) {
    console.log('onChange');
    console.log(schema);
  }
}
