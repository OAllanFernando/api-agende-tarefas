import dayjs from 'dayjs';
import { IUser } from 'app/shared/model/user.model';
import { ITag } from 'app/shared/model/tag.model';

export interface ITask {
  id?: number;
  title?: string | null;
  description?: string | null;
  executionTime?: dayjs.Dayjs | null;
  durationMin?: number | null;
  closed?: boolean | null;
  user?: IUser | null;
  tags?: ITag[] | null;
}

export const defaultValue: Readonly<ITask> = {
  closed: false,
};
