import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { PinguinAppPartnerModule } from './partner/partner.module';
import { PinguinAppPartnerAccountModule } from './partner-account/partner-account.module';
import { PinguinAppPartnerRoleModule } from './partner-role/partner-role.module';
import { PinguinAppPartnerUserModule } from './partner-user/partner-user.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        PinguinAppPartnerModule,
        PinguinAppPartnerAccountModule,
        PinguinAppPartnerRoleModule,
        PinguinAppPartnerUserModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PinguinAppEntityModule {}
