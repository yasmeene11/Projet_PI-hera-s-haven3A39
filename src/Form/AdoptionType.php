<?php

namespace App\Form;

use App\Entity\Adoption;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
class AdoptionType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        // Use condition to include/exclude fields based on the context
        if ($options['is_admin']) {
            // Admin has access to all fields
            $builder
                ->add('Adoption_Date')
                ->add('Adoption_Status', ChoiceType::class, [
                    'choices' => [
                        'Available' => 'Available',
                'Pending' => 'Pending',
                'Adopted' => 'Adopted',
                    ],
                    'placeholder' => 'Select Status',
                    'required' => true,
                ])
                ->add('Adoption_Fee')
                ->add('Account_Key')
                ->add('Animal_Key');
        } else {
            // Front-end user can only select the date
            $builder->add('Adoption_Date');
        }
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Adoption::class,
            // Add a custom option to pass the role/context information
            'is_admin' => false,
        ]);
    }
}
