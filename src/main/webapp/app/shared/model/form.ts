export interface SubmissionAccess {
  type: string;
  roles: string[];
}

export interface Validate {
  required: boolean;
  minLength: number;
  maxLength: number;
  pattern: string;
  custom: string;
  customPrivate: boolean;
}

export interface Conditional {
  show: string;
  when?: any;
  eq: string;
}

// eslint-disable-next-line @typescript-eslint/no-empty-interface
export interface Properties {}

export interface Component {
  autofocus: boolean;
  input: boolean;
  tableView: boolean;
  inputType: string;
  inputMask: string;
  label: string;
  key: string;
  placeholder: string;
  prefix: string;
  suffix: string;
  multiple: boolean;
  defaultValue: string;
  protected: boolean;
  unique: boolean;
  persistent: boolean;
  hidden: boolean;
  clearOnHide: boolean;
  spellcheck: boolean;
  validate: Validate;
  conditional: Conditional;
  type: string;
  labelPosition: string;
  inputFormat: string;
  tags: any[];
  properties: Properties;
  size: string;
  leftIcon: string;
  rightIcon: string;
  block?: boolean;
  action: string;
  disableOnInvalid?: boolean;
  theme: string;
}

export interface Access {
  type: string;
  roles: string[];
}

export interface RootObject {
  _id: string;
  title: string;
  name: string;
  path: string;
  type: string;
  display: string;
  tags: string[];
  submissionAccess: SubmissionAccess[];
  owner: string;
  components: Component[];
  access: Access[];
  created: Date;
  modified: Date;
  machineName: string;
}

export class Forms implements RootObject {
  _id: string;
  title: string;
  name: string;
  path: string;
  type: string;
  display: string;
  tags: string[];
  submissionAccess: SubmissionAccess[];
  owner: string;
  components: Component[];
  access: Access[];
  created: Date;
  modified: Date;
  machineName: string;
}
