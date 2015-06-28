show tables;

select * from usrdata;

select * from memdata;

select * from bininfo;

insert into memdata values ('621354','BCLMEM','Banque pour le Commerce Exterieur Lao Public','+85621213200','+85621262550','No.1 Pangkham Street, Chanthabouly District','Y','Member') ;

insert into memdata values ('668899','LDBMEM','Lao Development Bank','+85621213300','+856213304','No.13 Souphouvong Road, Ban Sihom, Chanthabouly District','Y','Member');

insert into memdata values ('220699','LVBMEM','Lao Viet Bank','+856251418','+856212514','No.44 LaneXang Anevue, Ban Hatsady Tay, Chanthabouly District','Y','Member');

insert into memdata values ('180893','APBMEM','Agriculture Promotion Bank','+856217130','+856217130','Kaysonephomvihan Road, Ban Phakhao, Xaythany District','Y','Member');

/* bin information download */

insert into bininfo (BIN, MEMDATA_IIN, CARDTYPE_TYPE) values ('668899','668899','BCNX');

insert into bininfo values ('621354','621354','BCNX');

insert into bininfo values ('888888','621354','BCNX');

insert into bininfo values ('220699','220699','BCNX');

insert into bininfo values ('180893','180893', 'BCNX');

