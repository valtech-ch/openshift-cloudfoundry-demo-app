/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PinguinAppTestModule } from '../../../test.module';
import { PartnerAccountDetailComponent } from 'app/entities/partner-account/partner-account-detail.component';
import { PartnerAccount } from 'app/shared/model/partner-account.model';

describe('Component Tests', () => {
    describe('PartnerAccount Management Detail Component', () => {
        let comp: PartnerAccountDetailComponent;
        let fixture: ComponentFixture<PartnerAccountDetailComponent>;
        const route = ({ data: of({ partnerAccount: new PartnerAccount('123') }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PinguinAppTestModule],
                declarations: [PartnerAccountDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(PartnerAccountDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PartnerAccountDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.partnerAccount).toEqual(jasmine.objectContaining({ id: '123' }));
            });
        });
    });
});
