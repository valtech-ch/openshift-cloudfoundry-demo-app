import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPartnerAccount } from 'app/shared/model/partner-account.model';
import { Principal } from 'app/core';
import { PartnerAccountService } from './partner-account.service';

@Component({
    selector: 'jhi-partner-account',
    templateUrl: './partner-account.component.html'
})
export class PartnerAccountComponent implements OnInit, OnDestroy {
    partnerAccounts: IPartnerAccount[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private partnerAccountService: PartnerAccountService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.partnerAccountService.query().subscribe(
            (res: HttpResponse<IPartnerAccount[]>) => {
                this.partnerAccounts = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInPartnerAccounts();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IPartnerAccount) {
        return item.id;
    }

    registerChangeInPartnerAccounts() {
        this.eventSubscriber = this.eventManager.subscribe('partnerAccountListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
