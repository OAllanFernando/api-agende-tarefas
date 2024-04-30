import { IUser } from 'app/shared/model/user.model';
import { ITask } from 'app/shared/model/task.model';

export interface ITag {
  id?: number;
  name?: string | null;
  user?: IUser | null;
  tasks?: ITask[] | null;
}

export const defaultValue: Readonly<ITag> = {};
