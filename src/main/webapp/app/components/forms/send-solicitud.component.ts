import { Component, Prop, Vue } from 'vue-property-decorator';

@Component
export default class SendSolicitudComponent extends Vue {
  @Prop({ default: 'NO-ASIGNADO' })
  public estado: string;

  @Prop({ required: true })
  public terminos: string;

  public selectedIcon = '';
  public accepted = false;

  public handleConfirmation() {
    this.$emit('send');
  }
}
