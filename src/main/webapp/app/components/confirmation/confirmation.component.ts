import Component from 'vue-class-component';
import { Prop, Vue } from 'vue-property-decorator';
@Component
export default class ConfirmationComponent extends Vue {
  @Prop({ default: 'confirmation' })
  id: string;

  @Prop({ default: 'Confirmación' })
  title: string;

  @Prop({ default: '¿Está seguro de ejecutar esta accion?' })
  question: string;

  public handleCancel(): void {
    this.hideModal();
  }

  public handleConfirmation(): void {
    this.hideModal();
    this.$emit('confirmed');
  }

  public hideModal() {
    (this.$refs[this.id] as any).hide();
  }
}
