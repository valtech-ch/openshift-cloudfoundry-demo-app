import { IPartnerAccount } from 'app/shared/model//partner-account.model';

export interface IPartnerUser {
    id?: string;
    userName?: string;
    accounts?: IPartnerAccount[];
}

export class PartnerUser implements IPartnerUser {
    constructor(public id?: string, public userName?: string, public accounts?: IPartnerAccount[]) {}
}
