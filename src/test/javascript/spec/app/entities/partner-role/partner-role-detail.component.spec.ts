/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PinguinAppTestModule } from '../../../test.module';
import { PartnerRoleDetailComponent } from 'app/entities/partner-role/partner-role-detail.component';
import { PartnerRole } from 'app/shared/model/partner-role.model';

describe('Component Tests', () => {
    describe('PartnerRole Management Detail Component', () => {
        let comp: PartnerRoleDetailComponent;
        let fixture: ComponentFixture<PartnerRoleDetailComponent>;
        const route = ({ data: of({ partnerRole: new PartnerRole('123') }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PinguinAppTestModule],
                declarations: [PartnerRoleDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(PartnerRoleDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PartnerRoleDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.partnerRole).toEqual(jasmine.objectContaining({ id: '123' }));
            });
        });
    });
});
