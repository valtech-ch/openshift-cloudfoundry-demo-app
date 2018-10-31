import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPartnerAccount } from 'app/shared/model/partner-account.model';

@Component({
    selector: 'jhi-partner-account-detail',
    templateUrl: './partner-account-detail.component.html'
})
export class PartnerAccountDetailComponent implements OnInit {
    partnerAccount: IPartnerAccount;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ partnerAccount }) => {
            this.partnerAccount = partnerAccount;
        });
    }

    previousState() {
        window.history.back();
    }
}
