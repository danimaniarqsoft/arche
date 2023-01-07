const I18N = {
  es: {
    Save: 'Guardar',
    'Add Another': 'Agregar',
    Delete: 'Borrar',
    Edit: 'Editar',
    Cancel: 'Cancelar',
    Submit: 'Guardar',
    'Text Field': 'Texto',
    'Text Area': 'Área de texto',
    Number: 'Número',
    Checkbox: 'Caja de verificación',
    'Select Boxes': 'Selector de verificación',
    Select: 'Selector',
    Radio: 'Radio',
    Button: 'Botón',
    Password: 'Contraseña',
    Email: 'Correo',
    'Phone Number': 'Teléfono',
    Tags: 'Etiquetas',
    Address: 'Dirección',
    'Date / Time': 'Fecha/Tiempo',
    Day: 'Día',
    Time: 'Tiempo',
    Currency: 'Moneda',
    Survey: 'Encuesta',
    Signature: 'Firma',
    'HTML Element': 'Elemento HTML',
    Content: 'Contenido',
    Columns: 'Columnas',
    'Field Set': 'Conjunto de campos',
    Panel: 'Panel',
    Table: 'Tabla',
    Tabs: 'Tabs',
    Well: 'Bien',
    Hidden: 'Oculto',
    Container: 'Contenedor',
    'Data Map': 'Mapa de datos',
    'Data Grid': 'Cuadrídula de datos',
    'Edit Grid': 'Editor de cuadrícula',
    Tree: 'Árbol',
    reCAPTCHA: 'reCAPTCHA',
    Resource: 'Recurso',
    File: 'Archivo',
    'Nested Form': 'Formulario anidado',
    Custom: 'Personalizado',
    'Label Position': 'Posición de la etiqueta',
    Label: 'Etiqueta',
    Description: 'Descripción',
    Preview: 'Vista preliminar',
    Display: 'Visualizar',
    Data: 'Datos',
    Validation: 'Validación',
    Conditional: 'Condiciones',
    Logic: 'Lógica',
    Layout: 'Diseño',
    'Text Field Component': 'Campo de texto',
    Questions: 'Preguntas',
    'Drag and Drop a form component': 'Arrastre aquí un componente del menú',
  },
} as const;

class DefaultFormOptions {
  constructor(public readOnly?: boolean, public language?: string | null, public i18n?: any | null) {
    this.readOnly = false;
    this.language = 'es';
    this.i18n = I18N;
  }
}

class DefaultBuilderOptions {
  constructor(
    public readOnly?: boolean,
    public language?: string | null,
    public viewAsHtml?: boolean | null,
    public i18n?: any | null,
    public builder?: any | null
  ) {
    this.readOnly = false;
    this.language = 'es';
    this.viewAsHtml = false;
    this.i18n = I18N;
    this.builder = {
      resource: false,
      basic: false,
      advanced: false,
      data: false,
      premium: false,
      layout: false,
      customBasic: {
        title: 'Menú',
        default: true,
        weight: 0,
        components: {
          textfield: true,
          textarea: true,
          email: true,
          phoneNumber: true,
          survey: true,
          radio: true,
          select: true,
          tags: true,
          address: true,
          editGrid: true,
        },
      },
    };
  }
}
const defaultFormOptions = new DefaultFormOptions();
const defaultBuilderOptions = new DefaultBuilderOptions();
export { defaultFormOptions, defaultBuilderOptions };
