/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { PartnerRoleComponentsPage, PartnerRoleDeleteDialog, PartnerRoleUpdatePage } from './partner-role.page-object';

const expect = chai.expect;

describe('PartnerRole e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let partnerRoleUpdatePage: PartnerRoleUpdatePage;
    let partnerRoleComponentsPage: PartnerRoleComponentsPage;
    let partnerRoleDeleteDialog: PartnerRoleDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load PartnerRoles', async () => {
        await navBarPage.goToEntity('partner-role');
        partnerRoleComponentsPage = new PartnerRoleComponentsPage();
        expect(await partnerRoleComponentsPage.getTitle()).to.eq('pinguinApp.partnerRole.home.title');
    });

    it('should load create PartnerRole page', async () => {
        await partnerRoleComponentsPage.clickOnCreateButton();
        partnerRoleUpdatePage = new PartnerRoleUpdatePage();
        expect(await partnerRoleUpdatePage.getPageTitle()).to.eq('pinguinApp.partnerRole.home.createOrEditLabel');
        await partnerRoleUpdatePage.cancel();
    });

    it('should create and save PartnerRoles', async () => {
        const nbButtonsBeforeCreate = await partnerRoleComponentsPage.countDeleteButtons();

        await partnerRoleComponentsPage.clickOnCreateButton();
        await promise.all([partnerRoleUpdatePage.setRoleNameInput('roleName'), partnerRoleUpdatePage.partnerAccountSelectLastOption()]);
        expect(await partnerRoleUpdatePage.getRoleNameInput()).to.eq('roleName');
        await partnerRoleUpdatePage.save();
        expect(await partnerRoleUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await partnerRoleComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last PartnerRole', async () => {
        const nbButtonsBeforeDelete = await partnerRoleComponentsPage.countDeleteButtons();
        await partnerRoleComponentsPage.clickOnLastDeleteButton();

        partnerRoleDeleteDialog = new PartnerRoleDeleteDialog();
        expect(await partnerRoleDeleteDialog.getDialogTitle()).to.eq('pinguinApp.partnerRole.delete.question');
        await partnerRoleDeleteDialog.clickOnConfirmButton();

        expect(await partnerRoleComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
