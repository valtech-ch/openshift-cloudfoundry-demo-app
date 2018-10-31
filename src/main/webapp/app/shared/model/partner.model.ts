import { IPartnerAccount } from 'app/shared/model//partner-account.model';

export const enum PartnerType {
    ISSUER = 'ISSUER',
    INVESTOR = 'INVESTOR',
    OTHER = 'OTHER'
}

export interface IPartner {
    id?: string;
    partnerName?: string;
    partnerType?: PartnerType;
    accounts?: IPartnerAccount[];
}

export class Partner implements IPartner {
    constructor(public id?: string, public partnerName?: string, public partnerType?: PartnerType, public accounts?: IPartnerAccount[]) {}
}
