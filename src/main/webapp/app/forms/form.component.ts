import Component from 'vue-class-component';
import { Vue, Inject } from 'vue-property-decorator';
import LoginService from '@/account/login.service';
import { JsonForms, JsonFormsChangeEvent } from '@jsonforms/vue2';
import { mergeStyles, vanillaRenderers } from '@jsonforms/vue2-vanilla';
import { customStyles } from '@/forms/CustomStyles';
import '@jsonforms/vue2-vanilla/vanilla.css';
// mergeStyles combines all classes from both styles definitions

const renderers = [
  ...vanillaRenderers,
  // here you can add custom renderers
];

@Component({
  components: {
    'json-forms': JsonForms,
  },
})
export default class Form extends Vue {
  @Inject('loginService')
  private loginService: () => LoginService;
  success = false;
  error = false;

  public provide(): any {
    return {
      styles: mergeStyles(customStyles, { control: { root: 'my-control' } }),
    };
  }

  public styles = mergeStyles(customStyles, { control: { root: 'my-control' } });
  public renderers = Object.freeze(renderers);

  public schema = {
    properties: {
      name: {
        type: 'string',
        minLength: 1,
        description: "The task's name",
      },
      description: {
        title: 'Long Description',
        type: 'string',
      },
      done: {
        type: 'boolean',
      },
      dueDate: {
        type: 'string',
        format: 'date',
        description: "The task's due date",
      },
      rating: {
        type: 'integer',
        maximum: 5,
      },
      recurrence: {
        type: 'string',
        enum: ['Never', 'Daily', 'Weekly', 'Monthly'],
      },
      recurrenceInterval: {
        type: 'integer',
        description: 'Amount of days until recurrence',
      },
    },
  };

  public uischema = {
    type: 'HorizontalLayout',
    elements: [
      {
        type: 'VerticalLayout',
        elements: [
          {
            type: 'Control',
            scope: '#/properties/name',
          },
          {
            type: 'Control',
            scope: '#/properties/description',
            options: {
              multi: true,
            },
          },
          {
            type: 'Control',
            scope: '#/properties/done',
          },
        ],
      },
      {
        type: 'VerticalLayout',
        elements: [
          {
            type: 'Control',
            scope: '#/properties/dueDate',
          },
          {
            type: 'Control',
            scope: '#/properties/rating',
          },
          {
            type: 'Control',
            scope: '#/properties/recurrence',
          },
          {
            type: 'Control',
            scope: '#/properties/recurrenceInterval',
          },
        ],
      },
    ],
  };

  public data = {
    name: 'Send email to Adrian',
    description: 'Confirm if you have passed the subject\nHereby ...',
    done: true,
    recurrence: 'Daily',
    rating: 3,
  };

  public onChange(event: JsonFormsChangeEvent) {
    console.log(event.data);

    this.data = event.data;
  }

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.query.key) {
        vm.init(to.query.key);
      }
    });
  }

  public init(key: string): void {}

  public openLogin(): void {
    this.loginService().openLogin((<any>this).$root);
  }
}
