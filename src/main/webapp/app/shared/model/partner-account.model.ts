import { IPartner } from 'app/shared/model//partner.model';
import { IPartnerRole } from 'app/shared/model//partner-role.model';
import { IPartnerUser } from 'app/shared/model//partner-user.model';

export interface IPartnerAccount {
    id?: string;
    accountName?: string;
    partner?: IPartner;
    roles?: IPartnerRole[];
    partnerUser?: IPartnerUser;
}

export class PartnerAccount implements IPartnerAccount {
    constructor(
        public id?: string,
        public accountName?: string,
        public partner?: IPartner,
        public roles?: IPartnerRole[],
        public partnerUser?: IPartnerUser
    ) {}
}
