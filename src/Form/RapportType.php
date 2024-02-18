<?php
namespace App\Form;

use App\Entity\Appointment;
use App\Entity\Rapport;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Form\Extension\Core\Type\TextareaType;
use Symfony\Bridge\Doctrine\Form\Type\EntityType; // Import EntityType
use Doctrine\ORM\EntityRepository;

class RapportType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options)
    {
        $builder->add('Description', TextareaType::class);

        if (!$builder->getData() || null === $builder->getData()->getAppointmentKey()) {
            $builder->add('Appointment_Key', EntityType::class, [
                'class' => Appointment::class,
                'query_builder' => function (EntityRepository $er) {
    return $er->createQueryBuilder('a')
        ->leftJoin('a.rapport', 'r')
        ->where('r IS NULL') // Correctly checks for appointments without a linked rapport.
        ->orderBy('a.Appointment_Date', 'ASC');
},
                'choice_label' => function (Appointment $appointment) {
                    return sprintf('ID: %d - Date: %s - Time: %s - Status: %s',
                        $appointment->getAppointmentId(),
                        $appointment->getAppointmentDate()->format('Y-m-d'),
                        $appointment->getAppointmentTime()->format('H:i'),
                        $appointment->getAppointmentStatus()
                    );
                },
                'placeholder' => 'Select an Appointment',
                'required' => false,
            ]);
        }
    }

    public function configureOptions(OptionsResolver $resolver)
    {
        $resolver->setDefaults([
            'data_class' => Rapport::class,
            // Optionally add a new option to explicitly control this behavior outside the form
        ]);
    }
}