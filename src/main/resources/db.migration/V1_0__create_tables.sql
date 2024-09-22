  set search_path = project_planning;

  create table project_read_only (
    id uuid primary key,
    "key" varchar(7) not null,
    project_lead_id uuid null,
    default_assignee_id uuid null
  );

  revoke update on table project_read_only from ms_project_planning;

  create sequence issue_sequence;

  create table sprint (
    id uuid primary key,
    name varchar(200) default 'New Sprint',
    goal varchar(700) null,
    created_at timestamp default (now() at time zone 'utc'),
    last_updated_at timestamp default (now() at time zone 'utc'),
    last_updated_by_id uuid not null,
    start_date timestamp,
    due_date timestamp,
    creator_id uuid not null,
    project_id uuid
  );

  create table issue_label_relationship (
    label_id varchar(500),
    issue_id uuid,
    primary key (label_id, issue_id)
  );

  create table issue_label (
    id uuid primary key,
    label varchar(500) not null,
    project_id uuid
  );

  create table issue_status (
    id uuid primary key,
    name varchar(200) not null,
    description varchar(1000) null,
    custom boolean not null default false,
    project_id uuid
  );

  create table issue_type (
    id uuid primary key,
    name varchar(200) not null,
    description varchar(1000) null,
    icon_url varchar(200) null,
    custom boolean not null default false,
    project_id uuid
  );

  create table issue (
    id uuid primary key,
    name varchar(500) unique not null,
    summary varchar(500) not null,
    description text null,
    attachment_urls text[]  default ARRAY[]::text[],
    created_at timestamp default (now() at time zone 'utc'),
    last_updated_at timestamp default (now() at time zone 'utc'),
    last_updated_by_id uuid not null,
    point_estimate int not null,
    start_date timestamp,
    due_date timestamp,
    dtype varchar(31),
    assignee_id uuid,
    reporter_id uuid,
    creator_id uuid not null,
    issue_status_id uuid,
    issue_type_id uuid,
    project_id uuid,
    sprint_id uuid
  );





