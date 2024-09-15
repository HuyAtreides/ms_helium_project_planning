
select * from project_planning.project_read_only;
insert into project_planning.project_read_only (
   id,
   "key"
) values (
  '1d6846ce-0a75-4a39-a49e-dea0d4be8b40',
  'LUMON'
);

insert into project_planning.issue_status (
  id,
  name,
  project_id
) values (
  'b8a8a0bd-888b-45b5-845d-1c4b316c9288',
  'to_do',
  '1d6846ce-0a75-4a39-a49e-dea0d4be8b40'
);

insert into project_planning.issue_type (
  id,
  name,
  project_id
) values (
  'f998b0aa-050d-41ad-bcad-e7b4e2b5a17e',
  'user_story',
  '1d6846ce-0a75-4a39-a49e-dea0d4be8b40'
);