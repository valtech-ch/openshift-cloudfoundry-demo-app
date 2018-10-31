import { IPartnerAccount } from 'app/shared/model//partner-account.model';

export interface IPartnerRole {
    id?: string;
    roleName?: string;
    partnerAccount?: IPartnerAccount;
}

export class PartnerRole implements IPartnerRole {
    constructor(public id?: string, public roleName?: string, public partnerAccount?: IPartnerAccount) {}
}
