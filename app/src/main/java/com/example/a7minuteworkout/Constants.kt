package com.example.a7minuteworkout

object Constants {

    fun defaultExerciseList():ArrayList<ExerciseModal>{
        val exerciseList:ArrayList<ExerciseModal> = ArrayList()

        val jumpingJack = ExerciseModal(1,"Jumping Jacks",
            R.drawable.ic_jumping_jacks,
            false,
            false )

        exerciseList.add(jumpingJack)

        val wallSit = ExerciseModal(2, "Wall Sit", R.drawable.ic_wall_sit, false, false)
        exerciseList.add(wallSit)

        val pushUp = ExerciseModal(3, "Push Up", R.drawable.ic_push_up, false, false)
        exerciseList.add(pushUp)

        val abdominalCrunch =
            ExerciseModal(4, "Abdominal Crunch", R.drawable.ic_abdominal_crunch, false, false)
        exerciseList.add(abdominalCrunch)

        val stepUpOnChair =
            ExerciseModal(
                5,
                "Step-Up onto Chair",
                R.drawable.ic_step_up_onto_chair,
                false,
                false
            )
        exerciseList.add(stepUpOnChair)

        val squat = ExerciseModal(6, "Squat", R.drawable.ic_squat, false, false)
        exerciseList.add(squat)

        val tricepDipOnChair =
            ExerciseModal(
                7,
                "Tricep Dip On Chair",
                R.drawable.ic_triceps_dip_on_chair,
                false,
                false
            )
        exerciseList.add(tricepDipOnChair)

        val plank = ExerciseModal(8, "Plank", R.drawable.ic_plank, false, false)
        exerciseList.add(plank)

        val highKneesRunningInPlace =
            ExerciseModal(
                9, "High Knees Running In Place",
                R.drawable.ic_high_knees_running_in_place,
                false,
                false
            )
        exerciseList.add(highKneesRunningInPlace)

        val lunges = ExerciseModal(10, "Lunges", R.drawable.ic_lunge, false, false)
        exerciseList.add(lunges)

        val pushupAndRotation =
            ExerciseModal(
                11,
                "Push up and Rotation",
                R.drawable.ic_push_up_and_rotation,
                false,
                false
            )
        exerciseList.add(pushupAndRotation)

        val sidePlank = ExerciseModal(12, "Side Plank", R.drawable.ic_side_plank, false, false)
        exerciseList.add(sidePlank)

        return exerciseList

    }

}