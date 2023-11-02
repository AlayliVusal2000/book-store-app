ALTER TABLE public.test_table ADD address VARCHAR(255) NULL,
ADD name VARCHAR(50) NOT NULL AFTER `id`;